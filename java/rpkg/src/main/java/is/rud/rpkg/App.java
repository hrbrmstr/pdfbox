package is.rud.rpkg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

public class App {

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

}
