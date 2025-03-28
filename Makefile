# modified makefile

JAVAC = javac
JAVA = java
PYTHON = python3

SRC_DIR = src
BIN_DIR = bin
DOC_DIR = doc

SRC_FILES = $(wildcard $(SRC_DIR)/*.java)
CLASS_FILES = $(patsubst $(SRC_DIR)/%.java, $(BIN_DIR)/%.class, $(SRC_FILES))

# Default target
all: $(CLASS_FILES)

# Java compilation
$(BIN_DIR)/%.class: $(SRC_DIR)/%.java
	@mkdir -p $(BIN_DIR)
	cd $(SRC_DIR) && $(JAVAC) -d ../$(BIN_DIR) $(notdir $<)

# Generate Javadoc
javadoc:
	@mkdir -p $(DOC_DIR)
	javadoc -d $(DOC_DIR) -sourcepath $(SRC_DIR) $(SRC_FILES)

# Run Java applications
run-avl: all
	$(JAVA) -cp $(BIN_DIR) GenericsKbAVLApp

# Run Python graphing program
run-graph:
	$(PYTHON) $(SRC_DIR)/Graphing.py

# Clean rule
clean:
	rm -rf $(BIN_DIR)/* $(DOC_DIR)/*

.PHONY: all javadoc run-avl run-graph clean