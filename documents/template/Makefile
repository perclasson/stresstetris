# Om du har t.ex. template.tex och vill ha template.pdf skriver du
# make template.pdf

LATEX=latex
DVIPS=dvips -Ppdf -ta4

PDFLATEX=pdflatex
THUMBPDF=thumbpdf

all:	

clean:	
	rm -f *~ *.log *.aux *.out *.toc *.idx thumb*

cleanall:	clean
	rm -f *.dvi *.ps *.pdf

%.dvi:	%.tex
	$(LATEX) $<
	$(LATEX) $<

%.ps:	%.dvi
	$(DVIPS) $<

%.pdf:	%.tex
	$(PDFLATEX) $<
	$(THUMBPDF) $@
	$(PDFLATEX) $<
