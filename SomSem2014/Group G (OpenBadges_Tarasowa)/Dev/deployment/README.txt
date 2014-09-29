ON WINDOWS
-----------------------------------------------------------
Copy php_xdiff.dll to the folder with php extenstions, e.g.
c:\xampp\php\ext\
Look in file c:\xampp\php\php.ini for the next section:
;;;;;;;;;;;;;;;;;;;;;;
; Dynamic Extensions ;
;;;;;;;;;;;;;;;;;;;;;;
And add extension=php_xdiff.dll line
More details can be found here http://blog.robandhannah.co.uk/2010/10/php-xdiff-libxdiff-on-windows/

ON LINUX
-----------------------------------------------------------
http://blog.robandhannah.co.uk/2010/10/php-xdiff-installation-in-linux/