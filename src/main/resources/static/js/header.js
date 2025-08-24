document.addEventListener('DOMContentLoaded', () => {
    const toggle = document.getElementById('menu-toggle');
    const menu = document.getElementById('menu');

    if (toggle && menu) {
        toggle.addEventListener('click', (event) => {
            event.stopPropagation();
            menu.classList.toggle('menu-aberto');
        });

        document.addEventListener('click', (event) => {
            const clicouFora = !menu.contains(event.target) && !toggle.contains(event.target);
            if (clicouFora) {
                menu.classList.remove('menu-aberto');
            }
        });
    }

    if (typeof window.atualizarContadorCarrinho === 'function') {
        window.atualizarContadorCarrinho();
    }
});

window.obterCarrinho = function () {
    try {
        return JSON.parse(localStorage.getItem('carrinho')) || [];
    } catch (e) {
        console.error("Erro ao ler carrinho do localStorage:", e);
        return [];
    }
};

window.salvarCarrinho = function (carrinho) {
    try {
        localStorage.setItem('carrinho', JSON.stringify(carrinho));
    } catch (e) {
        console.error("Erro ao salvar carrinho no localStorage:", e);
    }
};

window.atualizarContadorCarrinho = function () {
    const carrinho = window.obterCarrinho();
    const total = carrinho.reduce((soma, item) => soma + item.quantidade, 0);
    const span = document.getElementById('carrinho-count');
    if (span) span.textContent = total;
};

window.addAoCarrinho = function(produto) {
    const carrinho = window.obterCarrinho();
    const existente = carrinho.find(item => item.id === produto.id);

    // Armazena a quantidade que o usuário quer adicionar
    const quantidadeAdicionar = produto.quantidade;

    if (existente) {
        // Se o produto já está no carrinho:
        // SOMA A QUANTIDADE EXISTENTE COM A QUANTIDADE QUE FOI ADICIONADA
        existente.quantidade += quantidadeAdicionar;
    } else {
        // Se o produto é novo:
        // USA A QUANTIDADE PASSADA PELO INPUT (que já está em produto.quantidade)
        carrinho.push(produto);

        // **OPCIONAL:** Se você ainda tinha a linha "produto.quantidade = 1;"
        // na versão antiga para produtos novos, pode removê-la se ela não for necessária.
        // O objeto `produto` já vem com a quantidade correta da página do produto.
    }

    window.salvarCarrinho(carrinho);
    window.atualizarContadorCarrinho();
};