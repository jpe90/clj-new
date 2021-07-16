(ns ^:no-doc clj.generate.def
  (:require [clj.generate.file :as gen-file]
            [clj.generate.ns :as gen-ns]
            [clj.new.templates :as tmpl]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(defn generate
  "Append the specified definition to the specified namespace.
  Create the namespace if necessary. The symbol should be
  fully-qualified: my.ns/my-var"
  [prefix name & [body]]
  (let [[ns-name the-sym] (str/split name #"/")
        path (tmpl/name-to-path ns-name)
        ext "clj"
        ns-file (io/file (str prefix "/" path "." ext))]
    (when-not (.exists ns-file)
      (gen-ns/generate prefix ns-name))
    (gen-file/generate prefix ns-name
      (str "\n(def " the-sym
           "\n  \"Generated by clj\""
           "\n  "body")\n")
      ext
      true)))
