# PhenoSim: Measuring Phenotype Semantic Similarity using Human Phenotype Ontology

**Authors: [Jiajie Peng](http://teacher.nwpu.edu.cn/peng) (jiajiepeng@nwpu.edu.cn), [Hansheng Xue](https://sites.google.com/site/xuehansh/) (xhs1892@gmail.com)**

## Overview

This directory contains core code of PhenoSim algorithm. See our [paper](http://ieeexplore.ieee.org/document/7822617/) for details on the algorithm.

If you make use of this code or the PhenoSim algorithm in your work, please cite the following paper:

```
@conference{peng2016phenosim,
year = {2016},
author = {Jiajie Peng, Hansheng Xue, Yukai Shao, Xuequn Shang, Yadong Wang, and Jin Chen},
booktitle = {Bioinformatics and Biomedicine (BIBM), 2016 IEEE International Conference on},
title = {Measuring Phenotype Semantic Similarity using Human Phenotype Ontology},
organization = {IEEE},
}
```

### Prerequisites

Recent versions of Java JDK, and Java Universal Network/Graph Framework(JUNG) are required.

### Datasets
The Human Phenotype Ontology (HPO) data were downloaded from the HPO official website [http://humanphenotype-ontology.github.io](http://humanphenotype-ontology.github.io/).

For performance evaluation, we simulate eight different patient datasets, including causative gene prediction and disease prediction.

* **Causative gene precidtion**: optimal patients with known causative genes, noisy patients with known causative genes, imprecision patients with known causative genes, imprecision & noisy patients with known causative genes
* **Disease precidtion**: optimal patients with known diseases, noisy patients with known diseases, imprecision patients with known diseases, imprecision & noisy patients with known diseases


## Basic examples

Explain how to run the automated tests for this system




