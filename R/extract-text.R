#' Extract text from a PDF document
#'
#' @md
#' @param path path to PDF file (is auto-expanded with [path.expand()])
#' @return data frame
#' @export
#' @examples
#' extract_text(
#'   system.file(
#'     "extdata", "imperfect-forward-secrecy-ccs15.pdf", package="pdfbox"
#'   )
#' )
extract_text <- function(path) {

  path <- path[1]
  path <- path.expand(path)

  if (file.exists(path)) {

    .extract_text <- J("is.rud.rpkg.App")$extract_text

    ret <- .extract_text(path)

    if (!is.jnull(ret$page)) {
      tmp <- data.frame(page = ret$page, text = ret$text,
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