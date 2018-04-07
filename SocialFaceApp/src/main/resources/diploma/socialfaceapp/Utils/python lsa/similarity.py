# -*- coding: utf-8 -*-
"""
Created on Thu Apr 20 02:24:45 2017

@author: krist
"""

import lsa_teaching
import os, inspect
import argparse

path = os.path.dirname(os.path.abspath(inspect.getfile(inspect.currentframe())))
lsaManager = lsa_teaching.LSA(path, [])
lsaManager.load_lsi()  
lsaManager.prepare_corpus_from_files()

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Processing the document.')
    parser.add_argument('input_file', type=open,
                       help='file to be processed')
    args = parser.parse_args()
    
    lsaManager.doc_similarities(args.input_file)