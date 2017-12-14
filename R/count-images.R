#' Count number of images in a PDF document
#'
#' @md
#' @param path path to PDF file (is auto-expanded with [path.expand()])
#' @return numeric
#' @export

image_count <- function(path) {

  path <- path[1]
  path <- path.expand(path)

  if (file.exists(path)) {

    .count_images <- J("is.rud.rpkg.App")$image_count

    return(.count_images(path))

  } else {
    stop(sprintf("%s not found.", call.=FALSE))
  }

}