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
	pwd
	jco componentize --disable all --wit interfaces/bot.wit --world-name arena --out js-bot/bot.wasm js-bot/bot.js

py-bot:
	@echo "Building Python bot..."
	cd py-bot && echo "???" && cd ..

engine:
	@echo "Building engine..."
	cd engine && echo "???" && cd ..

