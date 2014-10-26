(function($){
    // elements
    var sidebarToggle;
    var sidebar;

    // functions
    var toggleSidebar = function() {
        sidebarToggle.click(function(){
            sidebar.toggleClass('show');
        });
    };

    $(document).ready(function(){
        sidebarToggle = $('.sidebar-toggle');
        sidebar = $('.sidebar');
        toggleSidebar();

    });
})(jQuery);

