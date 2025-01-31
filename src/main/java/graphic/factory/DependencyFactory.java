package graphic.factory;

import change.BufferDeepCreation;
import change.Change;
import classDiagram.relationships.Dependency;
import graphic.GraphicComponent;
import graphic.GraphicView;
import graphic.entity.EntityView;
import graphic.relations.DependencyView;
import swing.SPanelDiagramComponent;
import utility.SMessageDialog;

import java.awt.*;

/**
 * DependencyFactory allows to create a new dependency view associated with a new association UML. Give this factory at
 * the graphic view using the method initNewComponent() for initialize a new factory. Next, graphic view will use the
 * factory to allow creation of a new component, according to the specificity of the factory.
 *
 * @author David Miserez
 * @version 1.0 - 25.07.2011
 */
public class DependencyFactory extends RelationFactory {
  public final String ERROR_CREATION_MESSAGE = "Dependency creation failed.\nYou must make a bond between two " +
                                               "entities (class or interface).";

  /**
   * Create a new factory allowing the creation of a dependency.
   *
   * @param parent the graphic view
   */
  public DependencyFactory(final GraphicView parent) {
    super(parent);
    stroke = new BasicStroke(1.2f, BasicStroke.CAP_BUTT,
                             BasicStroke.JOIN_MITER, 10.0f, new float[] {7.f}, 0.0f);

    GraphicView.setButtonFactory(SPanelDiagramComponent.getInstance()
                                                       .getBtnDependency());
  }

  @Override
  public GraphicComponent create() {
    if (componentMousePressed instanceof EntityView
        && componentMouseReleased instanceof EntityView) {
      final EntityView source = (EntityView) componentMousePressed;
      final EntityView target = (EntityView) componentMouseReleased;

      final Dependency dependency = new Dependency(source.getComponent(),
                                                   target.getComponent());
      final DependencyView d = new DependencyView(parent, source, target,
                                                  dependency, mousePressed, mouseReleased, true);

      parent.addLineView(d);
      classDiagram.addDependency(dependency);

      Change.push(new BufferDeepCreation(false, dependency));
      Change.push(new BufferDeepCreation(true, dependency));

      parent.unselectAll();
      d.setSelected(true);

      return d;
    }

    repaint();
    return null;
  }

  @Override
  protected boolean isFirstComponentValid() {
    return componentMousePressed instanceof EntityView;
  }

  @Override
  protected void drawExtremity(Graphics2D g2) {
    DependencyView.paintExtremity(g2, points.get(points.size() - 1),
                                  mouseLocation);
  }

  @Override
  protected void creationFailed() {
    SMessageDialog.showErrorMessage(ERROR_CREATION_MESSAGE);
  }

}
