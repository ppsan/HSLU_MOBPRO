PARAMS=-N --pdf-engine=xelatex -V documentclass=scrartcl -V papersize=a4 -V lang=de -V urlcolor=blue --toc
FONTS=-V mainfont="IBM Plex Serif" -V sansfont="IBM Plex Sans" -V monofont="IBM Plex Mono"
DATE=-V date="`date +'%d.%m.%Y'`"

MOBPRO_ZF.pdf: mobpro_zf.md
	pandoc -s $(PARAMS) $(FONTS) $(DATE) mobpro_zf.md -o MOBPRO_ZF.pdf