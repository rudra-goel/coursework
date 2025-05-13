%%
%3.1 string reversal
% You are asked to write the functionality into a function 
% and test the function with your full name. 
my_name = 'Rudra Goel';     %fill in your name
disp(revstring(my_name));       %finish the revstring function below

%%
function y = revstring(x)
% a function that reverses the input string
% x: input string
% y: output string
% Write your command below:
y = x(end:-1:1)

end
