package graphic.entity;

import classDiagram.IDiagramComponent;
import classDiagram.components.Attribute;
import classDiagram.components.ClassEntity;
import classDiagram.components.ConstructorMethod;
import classDiagram.components.Method;
import classDiagram.components.SimpleEntity;
import classDiagram.components.Visibility;
import graphic.GraphicView;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Represent the view of a class in UML structure.
 *
 * @author David Miserez
 * @version 1.0 - 25.07.2011
 */
public class ClassView extends SimpleEntityView {

  public static final String ACTION_ADD_CONSTRUCTOR = "AddConstructor";

  /**
   * Create a new view from the given class.
   *
   * @param parent the graphic view
   * @param component the class (UML)
   */
  public ClassView(GraphicView parent, ClassEntity component) {
    super(parent, component);
  }

  @Override
  protected void prepareNewAttribute(Attribute attribute) { }

  @Override
  protected void prepareNewMethod(Method method) { }

  @Override
  public void restore() {
    super.restore();
    parent.addEntityWithRelations(this, this.middleBounds());
    restoreEntity();
    repaint();
  }

  protected void restoreEntity() {
    if (parent.getClassDiagram().searchComponentById(getAssociatedComponent().getId()) == null)
      parent.getClassDiagram().addClassEntity(
          (ClassEntity) getAssociatedComponent());
  }

  @Override
  protected void initializeMenuItemsAddElements(JPopupMenu popupmenu) {
    popupMenu.add(makeMenuItem("Add constructor", ACTION_ADD_CONSTRUCTOR, "constructor"));
    super.initializeMenuItemsAddElements(popupmenu);
  }

  /**
   * Create a new method with default type and name, without parameter.
   */
  public void addConstructor() {
    ConstructorMethod method = new ConstructorMethod(getComponent().getName(),
                                                     Visibility.PUBLIC, ((SimpleEntity) component));
    prepareNewMethod(method);

    if (((SimpleEntity) component).addMethod(method, 0))
      component.notifyObservers(IDiagramComponent.UpdateMessage.ADD_METHOD);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals(ACTION_ADD_CONSTRUCTOR))
      addConstructor();
    else
      super.actionPerformed(e);
  }

}
