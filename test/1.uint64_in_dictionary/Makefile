all : 
	(../../mgrep -w word_divider.txt -f dictionary.txt < article.txt; echo "###"; ../../mgrep -w word_divider.txt -f dictionary.txt -c CaseFolding.txt < article.txt) > current
	diff standard current
