# Graphing the FuSSI output
rm (list=ls())    #clear workspace
getwd ()

# set working directory to location of your Graph_<run_ID>.csv file
setwd("/home/tiana/FuSSI/source")

rl <- read.csv ("Graph_s5UTR.csv")
library (ggplot2)
ggplot(data=rl, aes(x=rl$Position, y=log(rl$Relative_Likelihood), group = 1))+geom_line(size=1.0)+labs(x="Position in MSA", y="log(Relative Likelihood)")+scale_x_continuous(breaks=seq(0,500, 50))
