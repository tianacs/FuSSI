# Graphing the FuSSI output
rm (list=ls())    #clear workspace
getwd ()

# set working directory to location of your Graph_<run_ID>.csv file
setwd("/home/...")

rl <- read.csv ("Graph_<run_ID.csv")
library (ggplot2)
ggplot(data=rl, aes(x=rl$Position, y=log(rl$Relative_Likelihood), group = 1))+
labs(x="Position in MSA", y="log(Relative Likelihood)")+
  scale_x_continuous(breaks=seq(0,10000,500))+
  #highlight a region in your alignment using:
  geom_rect (data=NULL, aes (xmin = 101, xmax = 343, ymin=-Inf, ymax=Inf), colour = "grey",fill = "lightgreen", alpha=0.02)+
  geom_line(size=1.0)

