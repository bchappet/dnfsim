
/*
 * Copyright (C) 2012 - Michael Baudin
 * Copyright (C) 2011 - DIGITEO - Michael Baudin
 * Copyright (C) 2008 - INRIA
 *
 * This file must be used under the terms of the CeCILL.
 * This source file is licensed as described in the file COPYING, which
 * you should have received as part of this distribution.  The terms
 * are also available at
 * http://www.cecill.info/licences/Licence_CeCILL_V2-en.txt
 *
 */
 
#ifndef _UNIFRNG_PRIV_H_
#define _UNIFRNG_PRIV_H_

// warning C4996: 'sprintf': This function or variable may be unsafe. 
// Consider using sprintf_s instead. To disable deprecation, 
// use _CRT_SECURE_NO_WARNINGS. See online help for details.
#ifdef _MSC_VER
#pragma warning( disable : 4996 )
#endif

#define TRUE_ (1)
#define FALSE_ (0)

// If this function is non-NULL, it is used to print messages out.
// It can be configure with the public function "unifrng_msgsetfunction".
// If the function is not configured, the messages are printed in the console.
extern void (* unigrng_messagefunction)(char * message);

// Print error messages
void unigrng_messageprint(char * msg);

int i_indx(char *a, char *b, long  la, long  lb);


__END_DECLS

#endif /** _UNIFRNG_PRIV_H_   **/




