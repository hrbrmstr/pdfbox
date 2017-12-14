#' Extract text from a PDF document
#'
#' @md
#' @param path path to PDF file (is auto-expanded with [path.expand()])
#' @return character vector
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

  } else {
    stop(sprintf("%s not found.", call.=FALSE))
  }

}