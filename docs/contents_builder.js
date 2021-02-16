let homedir = "https://www.trpfrog.net";
let headElement = document.getElementsByTagName('head')[0];

document.addEventListener("DOMContentLoaded", function () {
    loadFonts();
    buildHeader();
    buildHamburger();
    buildFooter();
    setFavicon();
});

function setFavicon() {
    let html = "";
    html += '<link rel="apple-touch-icon" sizes="180x180" href="' + homedir + '/favicon/apple-touch-icon.png">';
    html += '<link rel="icon" type="image/png" sizes="32x32" href="' + homedir + '/favicon/favicon-32x32.png">';
    html += '<link rel="icon" type="image/png" sizes="16x16" href="' + homedir + '/favicon/favicon-16x16.png">';
    html += '<link rel="manifest" href="' + homedir + '/favicon/site.webmanifest">';
    html += '<link rel="mask-icon" href="' + homedir + '/favicon/safari-pinned-tab.svg" color="#90e200">';
    html += '<link rel="shortcut icon" href="' + homedir + '/favicon/favicon.ico">';
    html += '<meta name="msapplication-TileColor" content="#90e200">';
    html += '<meta name="msapplication-config" content="/favicon/browserconfig.xml">';
    html += '<meta name="theme-color" content="#66a928">';
    headElement.insertAdjacentHTML('beforeend', html);
}

function loadFonts() {
    let html = `
        <link href="https://fonts.googleapis.com/css?family=Comfortaa|M+PLUS+Rounded+1c:400,800|Questrial|Noto+Sans+JP&display=swap&subset=japanese" rel="stylesheet">
    `;
    headElement.insertAdjacentHTML('beforeend', html);
}

function buildHamburger() {
    let homedir = 'https://www.trpfrog.net/';
    let html = `
        <section id="mobile_menu"></section>
            <aside id="menu_background" onclick="toggleSideMenu();"></aside>
            <aside id="side_menu">
                <div id="side_header"></div>
                <div id="side_links">
                    <a href="#howtoplay"><div class="sidemenu_link">How To Play</div></a>
                    <a href="#gallery"><div class="sidemenu_link">Gallery</div></a>
                    <a href="#demo"><div class="sidemenu_link">Demo Video</div></a>
                    <a href="#trouble"><div class="sidemenu_link">Q & A</div></a>
                    <a href="#credit"><div class="sidemenu_link">Credit</div></a>
                </div>
            </aside>
        </section>
    `;
    document.getElementsByTagName('body')[0].insertAdjacentHTML('afterbegin', html);
}

function buildHeader() {
    let html = `
        <div id="header_wrapper">
                <h1><a href="https://www.trpfrog.net/medipro-game">Space Wandering</a></h1>
                <nav>
                    <ul>
                        <li><a href="https://github.com/trpfrog/medipro-game/releases" class="headerButton">Releases</a></li>
                        <li><a href="https://github.com/trpfrog/medipro-game" class="headerButton">GitHub</a></li>
                        <li><a href="https://www.trpfrog.net/medipro-game/javadoc" class="headerButton">Javadoc</a></li>
                    </ul>
                </nav>
                <div id="hamburger_menu" onclick="toggleSideMenu();">
                    <a class="menu-trigger">
                        <span></span>
                        <span></span>
                        <span></span>
                    </a>
                </div>
            </div>
    `;
    document.getElementsByTagName('header')[0]
        .insertAdjacentHTML('afterbegin', html);
}

function buildFooter() {
    let html = `
        <div id="footer_wrapper">
            <p id="copyright">
                ©︎ 2021 アタマつくり部 (淵野アタリ + つまみ + ちくわぶ)
            </p>
            <p id="share">
                <a href="`+ getTwitterUrl() + `"><img src="https://www.trpfrog.net/images/socialicons/twitter.svg" height="45" class="shareButton" alt="Share with Twitter"></a>
                <a href="`+ getFacebookUrl() + `"><img src="https://www.trpfrog.net/images/socialicons/facebook.svg" height="45" class="shareButton" alt="Share with Facebook"></a>
                <a href="`+ getLineUrl() + `"><img src="https://www.trpfrog.net/images/socialicons/line.svg" height="45" class="shareButton" alt="Share with LINE"></a>
            </p>
        </div>
    `;
    document.getElementsByTagName('footer')[0]
        .insertAdjacentHTML('beforeend', html);
}

let isSideMenuOpened = false;
function toggleSideMenu(){
    if(isSideMenuOpened){
        hideMenu();
    }else{
        showMenu();
    }
    isSideMenuOpened = !isSideMenuOpened;
}

function showMenu() {
    document.getElementsByTagName('body')[0].style.overflow = 'hidden';
    document.getElementById('menu_background').style.visibility = 'visible';
    document.getElementById('menu_background').style.opacity = 0;
    document.getElementById('menu_background').style.opacity = 0.7;


    document.getElementById('side_menu').style.right = '0';

    let btn = document.getElementsByClassName('menu-trigger')[0].children;
    
    let style = `
        top: 0;
        width: 43%;
        -webkit-transform: translate3d(1.5px, 3.6px, 0) rotate(45deg);
        transform: translate3d(1.5px, 3.6px, 0) rotate(45deg);
    `;
    btn[0].setAttribute('style', style);

    style = `
        top: 10px;
        width: 105%;
        -webkit-transform: translate3d(-1px, 0, 0) rotate(-45deg);
        transform: translate3d(-1px, 0, 0) rotate(-45deg);
    `;
    btn[1].setAttribute('style', style);

    style = `
        bottom: 0;
        width: 43%;
        -webkit-transform: translate3d(14px, -3.5px, 0) rotate(45deg);
        transform: translate3d(14px, -3.5px, 0) rotate(45deg);
    `;
    btn[2].setAttribute('style', style);

}

function hideMenu() {
    document.getElementsByTagName('body')[0].style.overflow = 'visible';
    document.getElementById('menu_background').style.visibility = 'hidden';
    document.getElementById('side_menu').style.right = '-260px';

    let btn = document.getElementsByClassName('menu-trigger')[0].children;

    let style = 'top: 0;';
    btn[0].setAttribute('style', style);

    style = 'top: 10px;';
    btn[1].setAttribute('style', style);

    style = 'bottom: 0;';
    btn[2].setAttribute('style', style);
}

function getTwitterUrl() {
    let url = "https://twitter.com/intent/tweet?";
    url += "text=" + document.title + "&";
    url += "url=" + location.href;
    return url;
}

function getFacebookUrl() {
    let url = "https://www.facebook.com/sharer/sharer.php?u=";
    url += location.href;
    return url;
}

function getLineUrl() {
    let url = "https://social-plugins.line.me/lineit/share?url=";
    url += location.href;
    return url;
}