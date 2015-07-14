#!/bin/bash
grunt build-minified
cd ../build
rm -rf taskbook-js/
unzip taskbook-js.zip -d taskbook-js && rm taskbook-js.zip
mv  -v taskbook-js/js/vendor/map/* taskbook-js/js/
rm -rf taskbook-js/js/vendor