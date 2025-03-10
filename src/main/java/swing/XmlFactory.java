package swing;

import classDiagram.ClassDiagram;
import graphic.GraphicView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlFactory {

  private static XmlFactory instance = new XmlFactory();

  public static Document getDocument() {
    return instance.generate();
  }

  private XmlFactory() { }

  private Document createNewDocument() {

    // Création du document.
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      return docBuilder.newDocument();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
    return null;
  }

  private Document generate() {
    // Réinitialisation du document.
    Document doc = createNewDocument();

    GraphicView view = MultiViewManager.getSelectedGraphicView();
    ClassDiagram model = view.getClassDiagram();

    // Elément principal (diagramme de classe).
    Element classDiagram = doc.createElement("classDiagram");
    classDiagram.appendChild(model.getXmlElement(doc));
    for (GraphicView gv : MultiViewManager.getAllGraphicViews())
      classDiagram.appendChild(gv.getXmlElement(doc));
    doc.appendChild(classDiagram);

    return doc;
  }

}
