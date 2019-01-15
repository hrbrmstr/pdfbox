#' Stop logging
#'
#' Toggle verbose rJava logging
#'
#' This function turns off the somewhat verbose rJava logging, most of which is
#' uninformative. It is called automatically when tabulizer is attached via
#' \code{library()}, \code{require}, etc. To keep logging on, load the package
#' namespace using \code{requireNamespace("pdfbox")} and reference functions
#' in using fully qualified references.
#'
#' @md
#' @note This resets a global Java setting and may affect logging of other rJava operations,
#'       requiring a restart of R.
#' @return `NULL`, invisibly.
#' @author Thomas J. Leeper <thosjleeper@@gmail.com>
#' @keywords internal
#' @export
#' @examples \dontrun{
#' stop_logging()
#' }
stop_logging <- function() {
  rJava::J("java.util.logging.LogManager")$getLogManager()$reset()
  invisible(NULL)
}