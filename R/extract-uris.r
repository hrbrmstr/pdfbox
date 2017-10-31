#' Extract URI annotations from a PDF document
#'
#' @md
#' @param path path to PDF file (is auto-expanded with [path.expand()])
#' @return `NULL` or a `data.frame`
#' @export
#' @examples
#' extract_uris(
#'   system.file(
#'     "extdata",
#'     "imperfect-forward-secrecy-ccs15.pdf",
#'     package="pdfbox"
#'   )
#' )
extract_uris <- function(path) {

  path <- path[1]
  path <- path.expand(path)

  if (file.exists(path)) {

    .extract_uris<- J("is.rud.rpkg.App")$extractURIs

    ret <- .extract_uris(path)

    if (!is.jnull(ret$page)) {
      tmp <- data.frame(page = ret$page, uri = ret$uri, text = ret$text,
                        stringsAsFactors=FALSE)
      class(tmp) <- c("tbl_df", "tbl", "data.frame")
      tmp
    } else {
      NULL
    }

  } else {
    stop(sprintf("%s not found.", call.=FALSE))
  }

}