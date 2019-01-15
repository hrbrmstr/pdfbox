context("text extraction")
test_that("we can do something", {

  extract_text(
    system.file(
      "extdata", "imperfect-forward-secrecy-ccs15.pdf", package="pdfbox"
    )
  ) -> x

  expect_equal(length(x), 2)

})

context("metadata extraction")
test_that("we can do something", {

  pdf_info(
    system.file(
      "extdata", "imperfect-forward-secrecy-ccs15.pdf", package="pdfbox"
    )
  ) -> x

  expect_equal(
    x[["title"]][1], "Imperfect Forward Secrecy: How Diffie-Hellman Fails in Practice"
  )

})

context("URI extraction")
test_that("we can do something", {

  extract_uris(
    system.file(
      "extdata",
      "imperfect-forward-secrecy-ccs15.pdf",
      package="pdfbox"
    )
  ) -> uris

  expect_equal(nrow(uris), 33)

})
