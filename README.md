
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

### Extract URI Annotations

``` r
extract_uris(
  system.file("extdata","imperfect-forward-secrecy-ccs15.pdf", package="pdfbox")
)
```

    ##    page                                                                                                  uri
    ## 1     1                                                                                   https://weakdh.org
    ## 2     6                                                                                          www.fbi.gov
    ## 3    12                                               http://cr.yp.to/factorization/smoothparts-20040510.pdf
    ## 4    12                                                                     http://caramel.loria.fr/p180.txt
    ## 5    12                                       http://www.hyperelliptic.org/tanja/SHARCS/talks06/thorsten.pdf
    ## 6    12                                       http://www.hyperelliptic.org/tanja/SHARCS/talks06/thorsten.pdf
    ## 7    13                                                                      https://www.olcf.ornl.gov/titan
    ## 8    13 http://www.spiegel.de/international/germany/inside-the-nsa-s-war-on-internet-security-a-1010361.html
    ## 9    13 http://www.spiegel.de/international/germany/inside-the-nsa-s-war-on-internet-security-a-1010361.html
    ## 10   13                                                                              http://www.sagemath.org
    ## 11   13           https://github.com/bumptech/stud/blob/19a7f19686bcdbd689c6fbea31f68a276e62d886/stud.c#L593
    ## 12   13           https://github.com/bumptech/stud/blob/19a7f19686bcdbd689c6fbea31f68a276e62d886/stud.c#L593
    ## 13   13                                   https://devcentral.f5.com/articles/ssl-profiles-part-5-ssl-options
    ## 14   13                                   https://devcentral.f5.com/articles/ssl-profiles-part-5-ssl-options
    ## 15   13                                                                 https://gforge.inria.fr/projects/ecm
    ## 16   13                                                          http://www.spiegel.de/media/media-35671.pdf
    ## 17   13                                                          http://www.spiegel.de/media/media-35529.pdf
    ## 18   13                                                      http://cryptome.org/2013/08/spy-budget-fy13.pdf
    ## 19   13                                                          http://www.spiegel.de/media/media-35514.pdf
    ## 20   13                                                          http://www.spiegel.de/media/media-35509.pdf
    ## 21   13                                                          http://www.spiegel.de/media/media-35515.pdf
    ## 22   13                                                          http://www.spiegel.de/media/media-35533.pdf
    ## 23   13                                                          http://www.spiegel.de/media/media-35519.pdf
    ## 24   13        http://www.nytimes.com/interactive/2013/11/23/us/politics/23nsa-sigint-strategy-document.html
    ## 25   13        http://www.nytimes.com/interactive/2013/11/23/us/politics/23nsa-sigint-strategy-document.html
    ## 26   13                                                          http://www.spiegel.de/media/media-35522.pdf
    ## 27   13                                                          http://www.spiegel.de/media/media-35513.pdf
    ## 28   13                                                          http://www.spiegel.de/media/media-35528.pdf
    ## 29   13                                                          http://www.spiegel.de/media/media-35526.pdf
    ## 30   13                                                          http://www.spiegel.de/media/media-35517.pdf
    ## 31   13                                                          http://www.spiegel.de/media/media-35527.pdf
    ## 32   13                                                          http://www.spiegel.de/media/media-35520.pdf
    ## 33   13                                                          http://www.spiegel.de/media/media-35551.pdf
    ##                                                           text
    ## 1                                                  WeakDH.org.
    ## 2                                                 www.fbi.gov.
    ## 3      http://cr.yp.to/factorization/smoothparts-20040510.pdf.
    ## 4                            http://caramel.loria.fr/p180.txt.
    ## 5                          http://www.hyperelliptic.org/tanja/
    ## 6                                 SHARCS/talks06/thorsten.pdf.
    ## 7                             https://www.olcf.ornl.gov/titan.
    ## 8                 http://www.spiegel.de/international/germany/
    ## 9    inside-the-nsa-s-war-on-internet-security-a-1010361.html.
    ## 10                                    http://www.sagemath.org.
    ## 11                      https://github.com/bumptech/stud/blob/
    ## 12       19a7f19686bcdbd689c6fbea31f68a276e62d886/stud.c#L593.
    ## 13                                                    https://
    ## 14 devcentral.f5.com/articles/ssl-profiles-part-5-ssl-options.
    ## 15                       https://gforge.inria.fr/projects/ecm.
    ## 16                http://www.spiegel.de/media/media-35671.pdf.
    ## 17                http://www.spiegel.de/media/media-35529.pdf.
    ## 18            http://cryptome.org/2013/08/spy-budget-fy13.pdf.
    ## 19                http://www.spiegel.de/media/media-35514.pdf.
    ## 20                http://www.spiegel.de/media/media-35509.pdf.
    ## 21                http://www.spiegel.de/media/media-35515.pdf.
    ## 22                http://www.spiegel.de/media/media-35533.pdf.
    ## 23                http://www.spiegel.de/media/media-35519.pdf.
    ## 24           http://www.nytimes.com/interactive/2013/11/23/us/
    ## 25               politics/23nsa-sigint-strategy-document.html.
    ## 26                http://www.spiegel.de/media/media-35522.pdf.
    ## 27                http://www.spiegel.de/media/media-35513.pdf.
    ## 28                http://www.spiegel.de/media/media-35528.pdf.
    ## 29                http://www.spiegel.de/media/media-35526.pdf.
    ## 30                http://www.spiegel.de/media/media-35517.pdf.
    ## 31                http://www.spiegel.de/media/media-35527.pdf.
    ## 32                http://www.spiegel.de/media/media-35520.pdf.
    ## 33                http://www.spiegel.de/media/media-35551.pdf.

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
