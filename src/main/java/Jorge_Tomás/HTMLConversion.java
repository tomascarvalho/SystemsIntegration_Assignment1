package Jorge_Tomás;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by jorgearaujo on 12/10/2017.
 */
public class HTMLConversion {

    Source xslFile  =  new StreamSource("stylesheet.xsl");
    Source xmlFile =  new StreamSource("file.xml");

    public void transformXML() throws TransformerException, FileNotFoundException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();


        OutputStream htmlFile = new FileOutputStream("HTMLConversion.html");
        Transformer transform = transformerFactory.newTransformer(xslFile);
        transform.transform(xmlFile, new StreamResult(htmlFile));
    }
    public static void main(String[] args) throws TransformerException, FileNotFoundException {
        HTMLConversion html =  new HTMLConversion();
        html.transformXML();

    }
}
