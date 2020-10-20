<%--
  Created by IntelliJ IDEA.
  User: misch
  Date: 27.09.2020
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
<script type="text/javascript">
    jQuery(document).ready(function($){
        var form = $("#lang > select option");

        form.each(function(){
            var attrValue = $(this).attr("value");

            if( cookie_language == attrValue ) {
                $(this).attr("selected", "");
            };

        });

        $('#lang select').on('change',  (function() {
            $(this).children(':selected').trigger('click');
        }));

        $("#lang > select option").click(function(){
            $("#lang").submit();
        });
    });
</script>
