document.addEventListener("DOMContentLoaded", function() {
    // Get an array of div elements by className
    let megamenuEl = document.getElementsByClassName("megamenu-menu");

    const toggleSubMenu = (e) => {
        const submenuEl = e.target.querySelector('.sub-menu-wrapper');
        submenuEl && submenuEl.classList.toggle('collapsed');
    }

    // Attach focus and blur listeners to each div element
    for (const menu of megamenuEl) {
        // menu.addEventListener("focus", toggleSubMenu);
        // menu.addEventListener("blur", toggleSubMenu);
        menu.addEventListener("mouseleave", toggleSubMenu);
        menu.addEventListener("mouseenter", toggleSubMenu);
    }
});
