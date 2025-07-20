.PHONY: all interfaces js-bot py-bot engine

all: interfaces js-bot py-bot engine
	@echo "Building all components..."
	$(MAKE) interfaces
	$(MAKE) js-bot
	$(MAKE) py-bot
	$(MAKE) engine

interfaces:
	@echo "Generating interfaces..."

js-bot:
	@echo "Building JavaScript bot..."
	cd js-bot && echo "???" && cd ..

py-bot:
	@echo "Building Python bot..."
	cd py-bot && echo "???" && cd ..

engine:
	@echo "Building engine..."
	cd engine && echo "???" && cd ..

