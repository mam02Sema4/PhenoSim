# PhenoSim: Measuring Phenotype Semantic Similarity using Human Phenotype Ontology

**Authors: [Jiajie Peng](http://teacher.nwpu.edu.cn/peng) (jiajiepeng@nwpu.edu.cn), [Hansheng Xue](https://xuehansheng.github.io/) (xhs1892@gmail.com)**

## Overview

This directory contains core code of PhenoSim algorithm. See our [paper](http://ieeexplore.ieee.org/document/7822617/) for details on the algorithm.

If you make use of this code or the PhenoSim algorithm in your work, please cite the following paper:
author = {Jiajie Peng, Hansheng Xue, Yukai Shao, Xuequn Shang, Yadong Wang, and Jin Chen},
```
@article{Peng2017A,
  title={A novel method to measure the semantic similarity of HPO terms},
  author={Peng, Jiajie and Xue, Hansheng and Shao, Yukai and Shang, Xuequn and Wang, Yadong and Chen, Jin},
  journal={International Journal of Data Mining & Bioinformatics},
  volume={17},
  number={2},
  pages={173},
  year={2017},
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




