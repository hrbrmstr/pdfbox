package is.rud.rpkg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDNameTreeNode;
import org.apache.pdfbox.pdmodel.common.COSObjectable;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.OperatorProcessor;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import is.rud.rpkg.PdfImageCounter;

class uridf {
  public Integer [] page;
  public String [] uri;
  public String [] text;
  public uridf() {
    page = null;
    uri = null;
    text = null;
  }
  public void populate(List<Integer> ret_page, List<String> ret_uri, List<String> ret_text) {
    page = ret_page.toArray(new Integer[ret_page.size()]);
    uri = ret_uri.toArray(new String[ret_uri.size()]);
    text = ret_text.toArray(new String[ret_text.size()]);
  }
}

class pgdf {
  public Integer [] page;
  public String [] text;
  public pgdf() {
    page = null;
    text = null;
  }
  public void populate(List<Integer> ret_page, List<String> ret_text) {
    page = ret_page.toArray(new Integer[ret_page.size()]);
    text = ret_text.toArray(new String[ret_text.size()]);
  }
}

public class App {

  public static uridf extractURIs(String pdfPath) throws IOException {

   PDDocument doc = null;

   List<Integer> ret_page = new ArrayList<Integer>();
   List<String> ret_uri = new ArrayList<String>();
   List<String> ret_text = new ArrayList<String>();

   try {
    doc = PDDocument.load(new File(pdfPath));
    int pageNum = 0;
    for (PDPage page: doc.getPages()) {
     pageNum++;
     PDFTextStripperByArea stripper = new PDFTextStripperByArea();
     List < PDAnnotation > annotations = page.getAnnotations();
   //first setup text extraction regions
     for (int j = 0; j < annotations.size(); j++) {
      PDAnnotation annot = annotations.get(j);

      if (getActionURI(annot) != null) {
       PDRectangle rect = annot.getRectangle();
     //need to reposition link rectangle to match text space
       float x = rect.getLowerLeftX();
       float y = rect.getUpperRightY();
       float width = rect.getWidth();
       float height = rect.getHeight();
       int rotation = page.getRotation();
       if (rotation == 0) {
        PDRectangle pageSize = page.getMediaBox();
      // area stripper uses java coordinates, not PDF coordinates
        y = pageSize.getHeight() - y;
      } else {
      // do nothing
      // please send us a sample file
      }

      Rectangle2D.Float awtRect = new Rectangle2D.Float(x, y, width, height);
      stripper.addRegion("" + j, awtRect);
    }
  }

  stripper.extractRegions(page);

  for (int j = 0; j < annotations.size(); j++) {
    PDAnnotation annot = annotations.get(j);
    PDActionURI uri = getActionURI(annot);
    if (uri != null) {
     String urlText = stripper.getTextForRegion("" + j);

     ret_page.add(pageNum);
     ret_uri.add(uri.getURI());
     ret_text.add(urlText.trim());

   }
 }
}
} finally {
  if (doc != null) doc.close();
}

uridf ret_df = new uridf();
if (ret_page.size() > 0) ret_df.populate(ret_page, ret_uri, ret_text);
return(ret_df);

}

private static PDActionURI getActionURI(PDAnnotation annot) {
 // use reflection to catch all annotation types that have getAction()
 // If you can't use reflection, then check for classes
 // PDAnnotationLink and PDAnnotationWidget, and call getAction() and check for a
 // PDActionURI result type
 try {
  Method actionMethod = annot.getClass().getDeclaredMethod("getAction");
  if (actionMethod.getReturnType().equals(PDAction.class)) {
   PDAction action = (PDAction) actionMethod.invoke(annot);
   if (action instanceof PDActionURI) return (PDActionURI) action;
 }
} catch (NoSuchMethodException e) {
} catch (IllegalAccessException  e) {
} catch (InvocationTargetException e) {
}
return null;
}

public static void extractAttachments(String pdfPath, String extractPath) throws IOException {

  PDDocument document = null;

  try {

    File input = new File(pdfPath);

    String filePath = input.getParent() + System.getProperty("file.separator");

    document = PDDocument.load(input);

    PDDocumentNameDictionary namesDictionary =
    new PDDocumentNameDictionary( document.getDocumentCatalog() );
    PDEmbeddedFilesNameTreeNode efTree = namesDictionary.getEmbeddedFiles();

    if (efTree != null) {

      Map<String, PDComplexFileSpecification> names = efTree.getNames();

      if (names != null) {
        extractFiles(names, filePath);
      } else {

        List<PDNameTreeNode<PDComplexFileSpecification>> kids = efTree.getKids();
        for (PDNameTreeNode<PDComplexFileSpecification> node : kids) {
          names = node.getNames();
          extractFiles(names, filePath);
        };

      };

    };

    for (PDPage page : document.getPages()) {
      for (PDAnnotation annotation : page.getAnnotations()) {
        if (annotation instanceof PDAnnotationFileAttachment) {
          PDAnnotationFileAttachment annotationFileAttachment = (PDAnnotationFileAttachment) annotation;
          PDComplexFileSpecification fileSpec = (PDComplexFileSpecification) annotationFileAttachment.getFile();
          PDEmbeddedFile embeddedFile = getEmbeddedFile(fileSpec);
          extractFile(filePath, fileSpec.getFilename(), embeddedFile);
        };
      };
    };

  } finally {

  };

};


private static void extractFiles(Map<String, PDComplexFileSpecification> names, String filePath) throws IOException {
  for (Entry<String, PDComplexFileSpecification> entry : names.entrySet()) {
    String filename = entry.getKey();
    PDComplexFileSpecification fileSpec = entry.getValue();
    PDEmbeddedFile embeddedFile = getEmbeddedFile(fileSpec);
    extractFile(filePath, filename, embeddedFile);
  }
}

private static void extractFile(String filePath, String filename, PDEmbeddedFile embeddedFile) throws IOException {
  String embeddedFilename = filePath + filename;
  File file = new File(filePath + filename);
  System.out.println("Writing " + embeddedFilename);
  try {
    FileOutputStream fos = new FileOutputStream(file);
    fos.write(embeddedFile.toByteArray());
  } finally {};
}

private static PDEmbeddedFile getEmbeddedFile(PDComplexFileSpecification fileSpec ) {
  PDEmbeddedFile embeddedFile = null;
  if (fileSpec != null) {
    embeddedFile = fileSpec.getEmbeddedFileUnicode();
    if (embeddedFile == null) embeddedFile = fileSpec.getEmbeddedFileDos();
    if (embeddedFile == null) embeddedFile = fileSpec.getEmbeddedFileMac();
    if (embeddedFile == null) embeddedFile = fileSpec.getEmbeddedFileUnix();
    if (embeddedFile == null) embeddedFile = fileSpec.getEmbeddedFile();
  };
  return(embeddedFile);
}

public static long image_count(String pdfPath) throws IOException {

  PDDocument document = null;
  long count = 0;

  try {

    File input = new File(pdfPath);

    String filePath = input.getParent() + System.getProperty("file.separator");

    document = PDDocument.load(input);

    PdfImageCounter counter = new PdfImageCounter();
    for (PDPage pdPage : document.getPages()) {
     counter.processPage(pdPage);
   }

   count = counter.getDocumentImageCount();

 } finally {};

 return(count);

}

public static PDDocumentInformation pdf_info(String pdfPath) {

  PDDocument document = null;

  try {

    File input = new File(pdfPath);

    String filePath = input.getParent() + System.getProperty("file.separator");

    document = PDDocument.load(input);
    return(document.getDocumentInformation());

 } catch (IOException e) {
  return(null);
 }


}

public static pgdf extract_text(String pdfPath) {
  PDFTextStripper pdfStripper = null;
  PDDocument pdDoc = null;
  COSDocument cosDoc = null;
  List<Integer> ret_page = new ArrayList<Integer>();
  List<String> ret_text = new ArrayList<String>();
  File file = new File(pdfPath);
  try {
    PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
    parser.parse();
    cosDoc = parser.getDocument();
    pdfStripper = new PDFTextStripper();
    pdDoc = new PDDocument(cosDoc);
    int pgct = pdDoc.getNumberOfPages();

    for(int i=1; i<=pgct; i++) {

      pdfStripper.setStartPage(i);
      pdfStripper.setEndPage(i);

      String parsedText = pdfStripper.getText(pdDoc);

      ret_page.add(i);
      ret_text.add(parsedText);

    }

    pgdf ret_df = new pgdf();
    if (ret_page.size() > 0) ret_df.populate(ret_page, ret_text);
    return(ret_df);

  } catch (IOException e) {
    return(null);
  } 
}
}



