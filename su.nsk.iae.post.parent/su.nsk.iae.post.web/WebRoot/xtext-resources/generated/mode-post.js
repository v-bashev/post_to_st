define(["ace/lib/oop", "ace/mode/text", "ace/mode/text_highlight_rules"], function(oop, mText, mTextHighlightRules) {
	var HighlightRules = function() {
		var keywords = "ACTIVE|ARRAY|AT|BY|CASE|CONSTANT|DO|ELSE|ELSIF|END_CASE|END_FOR|END_FUNCTION_BLOCK|END_IF|END_PROCESS|END_PROGRAM|END_REPEAT|END_STATE|END_TIMEOUT|END_VAR|END_WHILE|ERROR|EXIT|FOR|FUNCTION_BLOCK|IF|IN|INACTIVE|LOOPED|MOD|NEXT|NOT|OF|PROCESS|PROGRAM|REPEAT|RESET|RESTART|RETURN|SET|START|STATE|STOP|THEN|TIMEOUT|TIMER|TO|UNTIL|VAR|VAR_EXTERNAL|VAR_GLOBAL|VAR_INPUT|VAR_IN_OUT|VAR_OUTPUT|VAR_TEMP|WHILE";
		this.$rules = {
			"start": [
				{token: "lparen", regex: "[\\[(]"},
				{token: "rparen", regex: "[\\])]"},
				{token: "keyword", regex: "\\b(?:" + keywords + ")\\b"}
			]
		};
	};
	oop.inherits(HighlightRules, mTextHighlightRules.TextHighlightRules);
	
	var Mode = function() {
		this.HighlightRules = HighlightRules;
	};
	oop.inherits(Mode, mText.Mode);
	Mode.prototype.$id = "xtext/post";
	Mode.prototype.getCompletions = function(state, session, pos, prefix) {
		return [];
	}
	
	return {
		Mode: Mode
	};
});
