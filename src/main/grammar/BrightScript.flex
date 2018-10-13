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
Or = "or"
To = "to"
In = "in"
Mod = "mod"
Not = "not"
Sub = "sub"

Quote = "'"
Colon = ":"

// Operators
LeftShift = "<<"
RightShift = ">>"
LeftShiftAssign = {LeftShift} "="
RightShiftAssign = {RightShift} "="
AddAssign = "+="
SubAssin = "-="
MulAssign = "*="
DivAssign = "/="
// TODO: Finde more prefered name
BackDivAssign = "\\="
LessEq = "<="
GreatEq = ">="
Inequal = "<>"

// Types
Integer = "integer"
Float = "float"
Double = "double"
Boolean = "boolean"
String = "string"
Object = "object"
Dynamic = "dynamic"
Void = "void"

Identifier = [a-zA-Z\_][a-zA-Z\_0-9]*
StringLiteral = "\"" ~"\""

%state S_COMMENT, S_TYPE
%%

<S_TYPE> {
    {Integer} { yybegin(YYINITIAL); return T_INTEGER; }
    {Float} { yybegin(YYINITIAL); return T_FLOAT; }
    {Double} { yybegin(YYINITIAL); return T_DOUBLE; }
    {Boolean} { yybegin(YYINITIAL); return T_BOOLEAN; }
    {String} { yybegin(YYINITIAL); return T_STRING; }
    {Object} { yybegin(YYINITIAL); return T_OBJECT; }
    {Dynamic} { yybegin(YYINITIAL); return T_DYNAMIC; }
    {Void} { yybegin(YYINITIAL); return T_VOID; }
    {Function} { yybegin(YYINITIAL); return T_FUNCTION; }
}

{Quote} { yybegin(S_COMMENT); return T_QUOTE; }
{Rem} { yybegin(S_COMMENT); return T_REM; }
{Function} { return T_FUNCTION; }
{Sub} { return T_SUB; }
{As} { yybegin(S_TYPE); return T_AS; }
{If} { return T_IF; }
{Then} { return T_THEN; }
{End} { return T_END; }
{Else} { return T_ELSE; }
{For} { return T_FOR; }
{Step} { return T_STEP; }
{Exit} { return T_EXIT; }
{Return} { return T_RETURN; }
{Print} { return T_PRINT; }
{Dim} { return T_DIM; }
{Stop} { return T_STOP; }
{While} { return T_WHILE; }
{Goto} { return T_GOTO; }
{Each} { return T_EACH; }
{Colon} { return T_COLON; }
{And} { return T_AND; }
{Or} { return T_OR; }
{To} { return T_TO; }
{In} { return T_IN; }
{Mod} { return T_MOD; }
{Not} { return T_NOT; }
{StringLiteral} { return T_STRING_LITERAL; }
{Identifier} { return T_IDENTIFIER; }

{LeftShift} { return T_LEFT_SHIFT; }
{RightShift} { return T_RIGHT_SHIFT; }
{LeftShiftAssign} { return T_LEFT_SHIFT_ASSIGN; }
{RightShiftAssign} { return T_RIGHT_SHIFT_ASSIGN; }
{AddAssign} { return T_ADD_ASSIGN; }
{SubAssin} { return T_SUB_ASSIGN; }
{MulAssign} { return T_MUL_ASSIGN; }
{DivAssign} { return T_DIV_ASSIGN; }
{BackDivAssign} { return T_BACK_DIV_ASSIGN; }
{LessEq} { return T_LESS_EQ; }
{GreatEq} { return T_GREAT_EQ; }
{Inequal} { return T_INEQUAL; }

[:digit:]+ { return T_NUMBER; }

<S_COMMENT> ~{LineTerminator} { yybegin(YYINITIAL); return T_COMMENT; }

{WhiteSpace}+ { return WHITE_SPACE; }

[^] { return T_CHAR; }