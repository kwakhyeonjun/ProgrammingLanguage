package lexer;

import java.lang.annotation.Inherited;

import static lexer.TokenType.*;
import static lexer.TransitionOutput.*;


enum State {
	START {
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			char v = ch.value();
			switch ( ch.type() ) {
				case LETTER:
					context.append(v);
					return GOTO_ACCEPT_ID;
				case DIGIT:
					context.append(v);
					return GOTO_ACCEPT_INT;
				case SPECIAL_CHAR:
                    context.append(v);
                    return GOTO_MATCHED(fromSpecialCharactor(ch.value()), context.getLexime());
                case SHARP:
					context.append(v);
					return GOTO_SHARP;
                case SIGN:
                        context.append(v);
                        return GOTO_SIGN;
				case WS:
					return GOTO_START;
				case END_OF_STREAM:
					return GOTO_EOS;
				default:
					throw new AssertionError();
			}
		}
	},
	ACCEPT_ID {
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			char v = ch.value();
			switch ( ch.type() ) {
				case LETTER:
				case DIGIT:
					context.append(v);
					return GOTO_ACCEPT_ID;
                case QUESTION:
                	context.append(v);
                	String lexme = context.getLexime();
                    return GOTO_MATCHED(Token.ofName(lexme).type(), lexme);
				case SPECIAL_CHAR:
					return GOTO_FAILED;
                case SIGN:
                    return GOTO_SIGN;
				case WS:
				case END_OF_STREAM:
					String endlexme = context.getLexime();
					return GOTO_MATCHED(Token.ofName(endlexme).type(), endlexme);
				default:
					throw new AssertionError();
			}
		}
	},
	ACCEPT_INT {
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			char v = ch.value();
			switch ( ch.type() ) {
				case LETTER:
					return GOTO_FAILED;
				case DIGIT:
					context.append(v);
					return GOTO_ACCEPT_INT;
				case SPECIAL_CHAR:
					return GOTO_FAILED;
				case WS:
				case END_OF_STREAM:
					return GOTO_MATCHED(INT, context.getLexime());
				default:
					throw new AssertionError();
			}
		}
	},
	SIGN {
		@Override
		public TransitionOutput transit(ScanContext context) {
			Char ch = context.getCharStream().nextChar();
			char v = ch.value();
			switch ( ch.type() ) {
				case LETTER:
					return GOTO_FAILED;
				case DIGIT:
					context.append(v);
					return GOTO_ACCEPT_INT;
				case WS:
				case END_OF_STREAM:
					String lexme = context.getLexime();
					return GOTO_MATCHED(fromSpecialCharactor(lexme.charAt(0)), lexme);
				default:
					throw new AssertionError();
			}
		}
	},
	SHARP{
		@Override
		public TransitionOutput transit(ScanContext context){
			Char ch = context.getCharStream().nextChar();
			char v = ch.value();
			switch(v){
				case 'T':
					context.append(v);
                    return GOTO_MATCHED(TokenType.TRUE, context.getLexime());
				case 'F':
					context.append(v);
                    return GOTO_MATCHED(TokenType.FALSE, context.getLexime());
				default:
					throw new AssertionError();
			}
		}
	},
	MATCHED {
		@Override
		public TransitionOutput transit(ScanContext context) {
			throw new IllegalStateException("at final state");
		}
	},
	FAILED{
		@Override
		public TransitionOutput transit(ScanContext context) {
			throw new IllegalStateException("at final state");
		}
	},
	EOS {
		@Override
		public TransitionOutput transit(ScanContext context) {
			return GOTO_EOS;
		}
	};

	abstract TransitionOutput transit(ScanContext context);
}
