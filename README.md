
pdfbox
======

Create, Maniuplate and Extract Data from PDF Files (R Apache PDFBox wrapper)

Description
-----------

I came across this thread (<https://twitter.com/derekwillis/status/922138080043241473>) and it looks like some misguided folks are going to help promote the use of PDF documents as a legit way to dissemiante data, which means that we're likely to see more evil orgs and Government agencies try to use PDFs to hide data.

PDFs are barely useful as publication holders these days let alone data sources.

Apache [PDFBox](https://pdfbox.apache.org/index.html) is a project that provides a comprehensive suite of tools to do things with and to PDF documents.

The aim here is to fill in any gaps in [`pdftools`](https://github.com/ropensci/pdftools) since `poppler` may not try to accommodate all the stupidity that we're now likley to see.

What's Inside The Tin
---------------------

-   Nothing at the moment

The following functions are implemented:

-   Nothing at the moment

Installation
------------

``` r
devtools::install_github("hrbrmstr/pdfboxjars")
devtools::install_github("hrbrmstr/pdfbox")
```

Usage
-----

``` r
library(pdfbox)

# current verison
packageVersion("pdfbox")
```

    ## [1] '0.1.0'
