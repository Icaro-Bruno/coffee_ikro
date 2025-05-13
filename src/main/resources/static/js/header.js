// Executa quando o DOM estiver carregado
document.addEventListener('DOMContentLoaded', () => {
    // === MENU RESPONSIVO ===
    const toggle = document.getElementById('menu-toggle');
    const menu = document.getElementById('menu');

    if (toggle && menu) {
        toggle.addEventListener('click', (event) => {
            event.stopPropagation();
            menu.classList.toggle('show');
        });

        document.addEventListener('click', (event) => {
            if (!menu.contains(event.target) && !toggle.contains(event.target)) {
                menu.classList.remove('show');
            }
        });
    }

    // Atualiza o contador do carrinho ao carregar
    window.atualizarContadorCarrinho();
});


// === FUNÇÕES GLOBAIS DO CARRINHO ===
window.obterCarrinho = function () {
    return JSON.parse(localStorage.getItem('carrinho')) || [];
}

window.salvarCarrinho = function (carrinho) {
    localStorage.setItem('carrinho', JSON.stringify(carrinho));
}

window.atualizarContadorCarrinho = function () {
    const carrinho = window.obterCarrinho();
    const total = carrinho.reduce((soma, item) => soma + item.quantidade, 0);
    const span = document.getElementById('carrinho-count');
    if (span) span.textContent = total;
}
