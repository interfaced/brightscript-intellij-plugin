package com.interfaced.brs.lang.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.interfaced.brs.lang.psi.BSTypes.*;
import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
%%

%{
    public _BSLexer() {
        this((java.io.Reader)null);
    }
%}

%public
%class _BSLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode
%ignorecase


LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

// Reserved words
Function = "function"
If = "if"
Then = "then"
End = "end"
Else = "else"
For = "for"
Step = "step"
Exit = "exit"
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
To = "to"
In = "in"

Quote = "'"
Colon = ":"

// Designators
Str = "$"
Int = "%"
Float = "!"
Double = "#"

Identifier = [a-zA-Z\_][a-zA-Z\_0-9]*({Str}|{Int}|{Float}|{Double})?
StringLiteral = "\"" ~"\""

%state COMMENT
%%

{Quote} { yybegin(COMMENT); return T_QUOTE; }
{Rem} { yybegin(COMMENT); return T_REM; }
{Function} { return T_FUNCTION; }
{If} { return T_IF; }
{Then} { return T_THEN; }
{End} { return T_END; }
{Else} { return T_ELSE; }
{For} { return T_FOR; }
{Step} { return T_STEP; }
{Exit} { return T_EXIT; }
{As} { return T_AS; }
{Return} { return T_RETURN; }
{Print} { return T_PRINT; }
{Dim} { return T_DIM; }
{Stop} { return T_STOP; }
{While} { return T_WHILE; }
{Goto} { return T_GOTO; }
{Each} { return T_EACH; }
{Colon} { return T_COLON; }
{And} { return T_AND; }
{To} { return T_TO; }
{In} { return T_IN; }
{StringLiteral} { return T_STRING_LITERAL; }
{Identifier} { return T_IDENTIFIER; }

<COMMENT> ~{LineTerminator} { yybegin(YYINITIAL); return T_COMMENT; }

{WhiteSpace}+ { return WHITE_SPACE; }

[^] { return T_CHAR; }