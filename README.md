
[![Travis-CI Build
Status](https://travis-ci.org/hrbrmstr/pdfbox.svg?branch=master)](https://travis-ci.org/hrbrmstr/pdfbox)
[![Coverage
Status](https://codecov.io/gh/hrbrmstr/pdfbox/branch/master/graph/badge.svg)](https://codecov.io/gh/hrbrmstr/pdfbox)
[![CRAN\_Status\_Badge](http://www.r-pkg.org/badges/version/pdfbox)](https://cran.r-project.org/package=pdfbox)

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
  - `pdf_info`: Retrieve PDF Metadata

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
## [1] '0.3.0'
```

### PDF Info

``` r
pdf_info(
 system.file(
   "extdata", "imperfect-forward-secrecy-ccs15.pdf", package="pdfbox"
 )
) -> info

dplyr::glimpse(info)
## Observations: 1
## Variables: 7
## $ title             <chr> "Imperfect Forward Secrecy: How Diffie-Hellman Fails in Practice"
## $ subject           <chr> ""
## $ author            <chr> ""
## $ creation_date     <chr> "2015-08-21T11:06:23-04:00[GMT-04:00]"
## $ modification_date <chr> "2015-08-21T11:08:05-04:00[GMT-04:00]"
## $ producer          <chr> "pdfTeX-1.40.14"
## $ keywords          <chr> ""
```

### Extract URI Annotations

``` r
extract_uris(
  system.file("extdata","imperfect-forward-secrecy-ccs15.pdf", package="pdfbox")
)
## # A tibble: 33 x 3
##     page uri                                                                    text                                    
##    <int> <chr>                                                                  <chr>                                   
##  1     1 https://weakdh.org                                                     WeakDH.org.                             
##  2     6 www.fbi.gov                                                            www.fbi.gov.                            
##  3    12 http://cr.yp.to/factorization/smoothparts-20040510.pdf                 http://cr.yp.to/factorization/smoothpar…
##  4    12 http://caramel.loria.fr/p180.txt                                       http://caramel.loria.fr/p180.txt.       
##  5    12 http://www.hyperelliptic.org/tanja/SHARCS/talks06/thorsten.pdf         http://www.hyperelliptic.org/tanja/     
##  6    12 http://www.hyperelliptic.org/tanja/SHARCS/talks06/thorsten.pdf         SHARCS/talks06/thorsten.pdf.            
##  7    13 https://www.olcf.ornl.gov/titan                                        https://www.olcf.ornl.gov/titan.        
##  8    13 http://www.spiegel.de/international/germany/inside-the-nsa-s-war-on-i… http://www.spiegel.de/international/ger…
##  9    13 http://www.spiegel.de/international/germany/inside-the-nsa-s-war-on-i… inside-the-nsa-s-war-on-internet-securi…
## 10    13 http://www.sagemath.org                                                http://www.sagemath.org.                
## # … with 23 more rows
```

### Extract text

``` r
extract_text(
  system.file(
    "extdata", "imperfect-forward-secrecy-ccs15.pdf", package="pdfbox"
  )
) -> pg_df

dplyr::glimpse(pg_df)
## Observations: 13
## Variables: 2
## $ page <int> 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13
## $ text <chr> "Imperfect Forward Secrecy:\nHow Diffie-Hellman Fails in Practice\nDavid Adrian¶ Karthikeyan Bhargavan∗ …
```

### pdfbox Metrics

| Lang  | \# Files |  (%) | LoC |  (%) | Blank lines |  (%) | \# Lines |  (%) |
| :---- | -------: | ---: | --: | ---: | ----------: | ---: | -------: | ---: |
| Java  |        3 | 0.18 | 352 | 0.57 |          89 | 0.51 |       23 | 0.15 |
| R     |       10 | 0.59 | 132 | 0.21 |          47 | 0.27 |       77 | 0.50 |
| XML   |        1 | 0.06 |  69 | 0.11 |           0 | 0.00 |        0 | 0.00 |
| Rmd   |        1 | 0.06 |  27 | 0.04 |          31 | 0.18 |       52 | 0.34 |
| Maven |        1 | 0.06 |  27 | 0.04 |           3 | 0.02 |        1 | 0.01 |
| make  |        1 | 0.06 |  10 | 0.02 |           5 | 0.03 |        1 | 0.01 |

## Code of Conduct

Please note that this project is released with a [Contributor Code of
Conduct](CONDUCT.md). By participating in this project you agree to
abide by its terms.
