#language: pt
Funcionalidade: Comprar produtos
  Cenário: Fazer compras no site do Extra
    Dado que eu esteja no site do "https://extra.com.br"
    Quando procurar pelo "<produto>"
    Então eu devo poder colocar o "<produto>" no carrinho

    Exemplos:
      |produto|
      |Café torrado e moído Aviação 500g|
      |Barbeador Philips Aquatouch 3D Seco Molhado - S112141 Bivolt|
