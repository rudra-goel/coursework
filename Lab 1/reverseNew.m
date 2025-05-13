function a = reverseNew
   ss = input('Enter String: ', 's');
   a = ss(end:-1:1)         %end of the string to 1 and offset by -1
   
end