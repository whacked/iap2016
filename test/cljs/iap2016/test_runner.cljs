(ns iap2016.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [iap2016.core-test]))

(enable-console-print!)

(doo-tests 'iap2016.core-test)
