#' Extract attachments embedded in a PDF document
#'
#' @param pdf_path,output_path paths (they get processed by [path.expand()]) for
#'        the input source PDF document and the directory where they should be
#'        extracted to.
#' @export
#' @examples
#' pdf_ex <- system.file("extdata", "LeedsBinsOctober2017_Summary.pdf", package="pdfbox")
#' td <- tempdir()
#' extract_attachments(pdf_ex, td)
extract_attachments <- function(pdf_path, output_path) {

  pdf_path <- path.expand(pdf_path)
  output_path <- path.expand(output_path)

  if (!file.exits(pdf_path)) stop("PDF file not found", call.=FALSE)
  if (!dir.exists(output_path)) stop("Output directory not found", call.=FALSE)

  app <- new(J("is.rud.rpkg.App"))

  app$extractAttachments(pdf_path, output_path)

}
