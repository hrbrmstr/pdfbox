
# pdfbox

Create, Maniuplate and Extract Data from PDF Files (R Apache PDFBox
wrapper)

## Description

I came across this thread
(<https://twitter.com/derekwillis/status/922138080043241473>) and it
looks like some misguided folks are going to help promote the use of PDF
documents as a legit way to dissemiante data, which means that we’re
likely to see more evil orgs and Government agencies try to use PDFs to
hide data.

PDFs are barely useful as publication holders these days let alone data
sources.

Apache [PDFBox](https://pdfbox.apache.org/index.html) is a project that
provides a comprehensive suite of tools to do things with and to PDF
documents.

The aim here is to fill in any gaps in
[`pdftools`](https://github.com/ropensci/pdftools) since `poppler` may
not try to accommodate all the stupidity that we’re now likley to see.

## What’s Inside The Tin

  - The ability to extract URI annotations

The following functions are implemented:

  - `extract_uris`: Extract URI annotations from a PDF document
  - `extract_text`: Extract text from a PDF document

## Installation

``` r
devtools::install_github("hrbrmstr/pdfboxjars")
devtools::install_github("hrbrmstr/pdfbox")
```

## Usage

``` r
library(pdfbox)

# current verison
packageVersion("pdfbox")
```

    ## [1] '0.2.0'

### PDF Info

``` r
pdf_info(
 system.file(
   "extdata", "imperfect-forward-secrecy-ccs15.pdf", package="pdfbox"
 )
) -> info

dplyr::glimpse(info)
```

    ## Observations: 1
    ## Variables: 7
    ## $ title             <chr> "Imperfect Forward Secrecy: How Diffie-Hellman Fails in Practice"
    ## $ subject           <chr> ""
    ## $ author            <chr> ""
    ## $ creation_date     <chr> "Aug 21, 2015 11:06:23 AM"
    ## $ modification_date <chr> "Aug 21, 2015 11:08:05 AM"
    ## $ producer          <chr> "pdfTeX-1.40.14"
    ## $ keywords          <chr> ""

### Extract URI Annotations

``` r
extract_uris(
  system.file("extdata","imperfect-forward-secrecy-ccs15.pdf", package="pdfbox")
)
```

    ## # A tibble: 33 x 3
    ##     page                                                                                                  uri
    ##    <int>                                                                                                <chr>
    ##  1     1                                                                                   https://weakdh.org
    ##  2     6                                                                                          www.fbi.gov
    ##  3    12                                               http://cr.yp.to/factorization/smoothparts-20040510.pdf
    ##  4    12                                                                     http://caramel.loria.fr/p180.txt
    ##  5    12                                       http://www.hyperelliptic.org/tanja/SHARCS/talks06/thorsten.pdf
    ##  6    12                                       http://www.hyperelliptic.org/tanja/SHARCS/talks06/thorsten.pdf
    ##  7    13                                                                      https://www.olcf.ornl.gov/titan
    ##  8    13 http://www.spiegel.de/international/germany/inside-the-nsa-s-war-on-internet-security-a-1010361.html
    ##  9    13 http://www.spiegel.de/international/germany/inside-the-nsa-s-war-on-internet-security-a-1010361.html
    ## 10    13                                                                              http://www.sagemath.org
    ## # ... with 23 more rows, and 1 more variables: text <chr>

### Extract text

``` r
extract_text(
  system.file(
    "extdata", "imperfect-forward-secrecy-ccs15.pdf", package="pdfbox"
  )
) -> pg_df

dplyr::glimpse(pg_df)
```

    ## Observations: 13
    ## Variables: 2
    ## $ page <int> 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13
    ## $ text <chr> "Imperfect Forward Secrecy:\nHow Diffie-Hellman Fails in Practice\nDavid Adrian¶ Karthikeyan Bhargavan...
