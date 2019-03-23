package com.interfaced.brs.lang.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.interfaced.brs.lang.psi.BrsTypes.*;
import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
%%

%{
    public _BrsLexer() {
        this((java.io.Reader)null);
    }
%}

%public
%class _BrsLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode
%ignorecase

LineTerminator = \r|\n|\r\n
WhiteSpace     = [ \t\f]

// Reserved words
Function = "function"
If = "if"
Then = "then"

True = "true"
False = "false"
Invalid = "invalid"

End = "end"
EndIf = "endif"
EndFunction = "endfunction"
EndSub = "endsub"
EndFor = "endfor"
EndWhile = "endwhile"

Else = "else"
ElseIf = "elseif"
For = "for"
Step = "step"

Exit = "exit"
ExitWhile = "exitwhile"
ExitFor = "exitfor"

Next = "next"
As = "as"
Return = "return"
Print = "print"
Rem = "rem"
Dim = "dim"
Stop = "stop"
While = "while"
Goto = "goto"
Each = "each"
And = "and"
Or = "or"
To = "to"
In = "in"
Mod = "mod"
Not = "not"
Sub = "sub"
Library = "library"

// Types
Integer = "integer"
Float = "float"
Double = "double"
Boolean = "boolean"
String = "string"
Object = "object"
Dynamic = "dynamic"
Void = "void"
LongInteger = "longinteger"

Identifier = [a-zA-Z\_][a-zA-Z\_0-9]*
StringLiteral = "\"" ~"\""

Exponent = [edED] [-+]? {DecimalLit}
HexLit = "&h" [0-9a-fA-F]+
DecimalLit = [0-9]+

IntegerLit = {DecimalLit} | {HexLit}
FloatLit = {DecimalLit}? \. {DecimalLit} {Exponent}?

EndWS = {End} {WhiteSpace}+

%state S_COMMENT, S_TYPE, S_MEMBER
%%

<S_TYPE> {
    {Integer}  { yybegin(YYINITIAL); return T_INTEGER; }
    {Float}    { yybegin(YYINITIAL); return T_FLOAT; }
    {Double}   { yybegin(YYINITIAL); return T_DOUBLE; }
    {Boolean}  { yybegin(YYINITIAL); return T_BOOLEAN; }
    {String}   { yybegin(YYINITIAL); return T_STRING; }
    {Object}   { yybegin(YYINITIAL); return T_OBJECT; }
    {Dynamic}  { yybegin(YYINITIAL); return T_DYNAMIC; }
    {Void}     { yybegin(YYINITIAL); return T_VOID; }
    {Function} { yybegin(YYINITIAL); return T_FUNCTION_TYPE; }
    {LongInteger} { yybegin(YYINITIAL); return T_LONGINTEGER; }
}

<S_COMMENT> {
    .* { yybegin(YYINITIAL); return T_COMMENT; }
}

<S_MEMBER> {
    {Identifier} { yybegin(YYINITIAL); return T_IDENTIFIER; }
}

{As} { yybegin(S_TYPE); return T_AS; }

{Function} { return T_FUNCTION; }
{Sub} { return T_SUB; }
{If} { return T_IF; }
{Then} { return T_THEN; }
{End} { return T_END; }

{EndFor}      | ({EndWS} {For})      { return T_END_FOR; }
{EndWhile}    | ({EndWS} {While})    { return T_END_WHILE; }
{EndFunction} | ({EndWS} {Function}) { return T_END_FUNCTION; }
{EndIf}       | ({EndWS} {If})       { return T_END_IF; }
{EndSub}      | ({EndWS} {Sub})      { return T_END_SUB; }

{Else} { return T_ELSE; }
{ElseIf} { return T_ELSE_IF; }

{For} { return T_FOR; }
{Step} { return T_STEP; }

{Exit} { return T_EXIT; }
{ExitWhile} { return T_EXIT_WHILE; }
{ExitFor} { return T_EXIT_FOR; }

{Next} { return T_NEXT; }
{Return} { return T_RETURN; }
{Print} { return T_PRINT; }
{Dim} { return T_DIM; }
{Stop} { return T_STOP; }
{While} { return T_WHILE; }
{Goto} { return T_GOTO; }
{Each} { return T_EACH; }
{And} { return T_AND; }
{Or} { return T_OR; }
{To} { return T_TO; }
{In} { return T_IN; }
{Mod} { return T_MOD; }
{Not} { return T_NOT; }
{StringLiteral} { return T_STRING_LITERAL; }
({Rem} | "'") { yypushback(yylength()); yybegin(S_COMMENT); }
{True} { return T_TRUE; }
{False} { return T_FALSE; }
{Invalid} { return T_INVALID; }
{Library} { return T_LIBRARY; }
{Identifier} { return T_IDENTIFIER; }

{FloatLit} { return T_FLOAT_LIT; }
{IntegerLit} { return T_INTEGER_LIT; }

"{"   { return T_LBRACE;             }
"}"   { return T_RBRACE;             }
"["   { return T_LBRACK;             }
"]"   { return T_RBRACK;             }
"("   { return T_LPAREN;             }
")"   { return T_RPAREN;             }
":"   { return T_COLON;              }
","   { return T_COMMA;              }
"="   { return T_EQ;                 }
"."   {yybegin(S_MEMBER);return T_DOT;}
"<"   { return T_LESS;               }
">"   { return T_GREAT;              }
"++"  { return T_INCREMENT;          }
"--"  { return T_DECREMENT;          }
"+"   { return T_PLUS;               }
"-"   { return T_MINUS ;             }
"*"   { return T_ASTERISK;           }
"/"   { return T_SLASH;              }
"\\"  { return T_BACK_SLASH;         }
"$"   { return T_DOLLAR;             }
"%"   { return T_PERCENT;            }
"!"   { return T_EXCLAM;             }
"?"   { return T_QUESTION;           }
"#"   { return T_SHARP;              }
"&"   { return T_AMP;                }
"^"   { return T_EXP;                }
"<<"  { return T_LEFT_SHIFT;         }
">>"  { return T_RIGHT_SHIFT;        }
"<<=" { return T_LEFT_SHIFT_ASSIGN;  }
">>=" { return T_RIGHT_SHIFT_ASSIGN; }
"+="  { return T_ADD_ASSIGN;         }
"-="  { return T_SUB_ASSIGN;         }
"*="  { return T_MUL_ASSIGN;         }
"/="  { return T_DIV_ASSIGN;         }
"\\=" { return T_BACK_DIV_ASSIGN;    }
"<="  { return T_LESS_EQ;            }
">="  { return T_GREAT_EQ;           }
"<>"  { return T_INEQUAL;            }
";"   { return T_SEMICOLON;          }
"@"   {yybegin(S_MEMBER);return T_AT;}

{LineTerminator} { return T_LINE_TERMINATOR; }
{WhiteSpace}+ { return WHITE_SPACE; }

[^] { return BAD_CHARACTER; }