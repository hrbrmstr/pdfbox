#' Retrieve PDF Metadata
#'
#' TODO metadata
#'
#' @md
#' @param path path to PDF file (is auto-expanded with [path.expand()])
#' @return data.frame
#' @export
#' @examples
#' pdf_info(
#'   system.file(
#'     "extdata", "imperfect-forward-secrecy-ccs15.pdf", package="pdfbox"
#'   )
#' )
pdf_info <- function(path) {

  path <- path[1]
  path <- path.expand(path)

  if (file.exists(path)) {

    .pdf_info <- J("is.rud.rpkg.App")$pdf_info

    ret <- .pdf_info(path)

    if (!is.jnull(ret)) {

      fields <- sapply(.jevalArray(ret$getMetadataKeys()$toArray()), function(x) x$toString())

      data.frame(
        title = ret$getTitle(),
        subject = ret$getSubject(),
        author = ret$getAuthor(),
        creation_date = ret$getCreationDate()$getTime()$toLocaleString(),
        modification_date = ret$getModificationDate()$getTime()$toLocaleString(),
        producer = ret$getProducer(),
        keywords = ret$getKeywords(),
#        custom_metadata = ret$getCustomMetadataValue(),
        stringsAsFactors = FALSE
      ) -> tmp

      class(tmp) <- c("tbl_df", "tbl", "data.frame")

      tmp

    } else {
      NULL
    }

  } else {
    stop(sprintf("%s not found.", call.=FALSE))
  }

}

#  [1] "getTitle()"              "getPropertyStringValue(" "getKeywords()"
#  [4] "getSubject()"            "getAuthor()"             "getCOSObject()"
#  [7] "getCOSObject()"          "getCreator()"            "getProducer()"
# [10] "getCreationDate()"       "getModificationDate()"   "getTrapped()"
# [13] "getMetadataKeys()"       "getCustomMetadataValue(" "getClass()"