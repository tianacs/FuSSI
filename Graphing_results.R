# Graphing the FuSSI output
rm (list=ls())    #clear workspace
getwd ()

# set working directory to location of your Graph_<run_ID>.csv file
setwd("/home/tiana/FuSSI/HCV_Sequences/Output")
setwd ("C:/Users/Tiana/Desktop/Project/FuSSI/HCV_Sequences/Output")

rl <- read.csv ("Graph_Mauger250rerun.csv")
library (ggplot2)
ggplot(data=rl, aes(x=rl$Position, y=log(rl$Relative_Likelihood), group = 1))+
labs(x="Position in MSA", y="log(Relative Likelihood)")+
  scale_x_continuous(breaks=seq(0,10000,500))+
  geom_rect (data=NULL, aes (xmin = 101, xmax = 343, ymin=-Inf, ymax=Inf), colour = "grey",fill = "lightgreen", alpha=0.02)+
  geom_rect (data=NULL, aes (xmin = 9511, xmax = 9583, ymin=-Inf, ymax=Inf), colour = "grey", fill = "lightgreen", alpha=0.02)+
  geom_rect (data=NULL, aes (xmin = 9744, xmax = 9791, ymin=-Inf, ymax=Inf), colour = "grey", fill = "lightgreen", alpha=0.02)+
  geom_rect (data=NULL, aes (xmin = 765, xmax = 840, ymin=-Inf, ymax=Inf), colour = "grey", fill = "lightgreen", alpha=0.02)+
  geom_rect (data=NULL, aes (xmin = 8228, xmax = 8398, ymin=-Inf, ymax=Inf), colour = "grey",fill = "lightgreen", alpha=0.02)+
  geom_rect (data=NULL, aes (xmin = 9047, xmax = 9136, ymin=-Inf, ymax=Inf), colour = "grey",fill = "lightgreen", alpha=0.02)+
  geom_rect (data=NULL, aes (xmin = 9293, xmax = 9470, ymin=-Inf, ymax=Inf), colour = "grey",fill = "lightgreen", alpha=0.02)+
  geom_rect (data=NULL, aes (xmin = 402, xmax = 437, ymin=-Inf, ymax=Inf), colour = "grey",fill = "lightgreen", alpha=0.02)+
  geom_rect (data=NULL, aes (xmin = 441, xmax = 521, ymin=-Inf, ymax=Inf),colour = "grey", fill = "lightgreen", alpha=0.02)+
  geom_line(size=1.0)

