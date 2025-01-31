package classDiagram.components;

import change.BufferVariable;
import change.Change;
import classDiagram.ClassDiagram;
import classDiagram.IDiagramComponent;
import classDiagram.verifyName.VariableName;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Observable;

/**
 * Represent a variable in UML structure.
 *
 * @author David Miserez
 * @version 1.0 - 24.07.2011
 */
public class Variable extends Observable implements IDiagramComponent {
  public static final String REGEX_SEMANTIC_ATTRIBUTE = "[a-zA-Z_"
                                                        + Type.accents + "][\\w_" + Type.accents + "]*";

  public static boolean checkSemantic(String name) {
    return name.indexOf(' ') == -1;
    //return name.matches(REGEX_SEMANTIC_ATTRIBUTE);
  }

  protected boolean constant = false;

  protected final int id = ClassDiagram.getNextId();
  protected String name;
  protected Type type = PrimitiveType.VOID_TYPE;

  /**
   * Create a new variable with the given name and type.
   *
   * @param name the name for the variable
   * @param type the type for the variable
   */
  public Variable(String name, Type type) {
    boolean isBlocked = Change.isBlocked();
    Change.setBlocked(true);

    while (!setName(name))
      throw new IllegalArgumentException("Syntaxe error.");

    setType(type);

    Change.setBlocked(isBlocked);
  }

  /**
   * Constructor of copy.
   *
   * @param variable variable
   */
  public Variable(Variable variable) {
    this.name = variable.name;
    this.type = new Type(variable.type.getName());
  }

  public void setVariable(Variable variable) {
    boolean isRecord = Change.isRecord();
    Change.record();

    setName(variable.getName());
    setType(variable.getType());

    if (!isRecord) Change.stopRecord();

    notifyObservers();
  }

  @Override
  public int getId() {
    return id;
  }

  /**
   * Get the name for this variable.
   *
   * @return the name for this variable
   */
  public String getName() {
    return name;
  }

  /**
   * Get the type for this variable.
   *
   * @return the type for this variable.
   */
  public Type getType() {
    setChanged();

    return type;
  }

  @Override
  public void select() {
    setChanged();
  }

  /**
   * Set the name for this variable.
   *
   * @param name the new name for this variable
   *
   * @return {@code true} if the name has been set, otherwise {@code false}.
   */
  public boolean setName(final String name) {
    if (!VariableName.getInstance().verifyName(name) || name.equals(getName()))
      return false;

    Change.push(new BufferVariable(this));
    this.name = name;
    Change.push(new BufferVariable(this));

    setChanged();

    return true;
  }

  /**
   * Set the type for this variable.
   *
   * @param type the new type for this variable
   */
  public void setType(Type type) {
    if (getType() != null && type.getName().equals(getType().getName()))
      return;

    Change.push(new BufferVariable(this));
    this.type = type;
    Change.push(new BufferVariable(this));

    setChanged();
  }

  @Override
  public String toString() {
    return name + " : " + type;
  }

  @Override
  public String getXmlTagName() {
    return "variable";
  }

  @Override
  public Element getXmlElement(Document doc) {
    Element variable = doc.createElement(getXmlTagName());
    variable.setAttribute("name", name);
    variable.setAttribute("type", type.toString());
    variable.setAttribute("const", String.valueOf(constant));
    return variable;
  }

}
