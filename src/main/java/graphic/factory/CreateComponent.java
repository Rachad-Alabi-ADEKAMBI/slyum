package graphic.factory;

import classDiagram.ClassDiagram;
import graphic.GraphicComponent;
import graphic.GraphicView;
import swing.SPanelDiagramComponent;

import java.awt.*;

/**
 * CreateComponent allows to create a new graphic component view. Give this factory at the graphic view using the method
 * initNewComponent() for initialize a new factory. Next, graphic view will use the factory to allow creation of a new
 * component, according to the specificity of the factory.
 *
 * @author David Miserez
 * @version 1.0 - 25.07.2011
 */
public abstract class CreateComponent extends GraphicComponent {
  protected ClassDiagram classDiagram;
  protected GraphicComponent createdComponent;

  public CreateComponent(GraphicView parent) {
    super(parent);
    parent.deleteCurrentFactory();
    this.classDiagram = parent.getClassDiagram();
  }

  /**
   * Create an instance of the class with informations collected during life-cycle. Return null if informations
   * collected are not suffisant to create a class.
   *
   * @return the new class created or null.
   */
  public abstract GraphicComponent create();

  public void deleteFactory() {
    repaint();
    SPanelDiagramComponent.getInstance().applyMode();
  }

  @Override
  public Rectangle getBounds() {
    return null;
  }

  public Cursor getCursor() {
    return Cursor.getDefaultCursor();
  }

  public GraphicComponent getCreatedComponent() {
    return createdComponent;
  }

  @Override
  public boolean isAtPosition(Point mouse) {
    return false;
  }

  @Override
  public void setBounds(Rectangle bounds) { }

}
