function switchVisible() {
  if (document.getElementById('Div1')) {
    if (
      document.getElementById('Div1').style.display == 'none'
    ) {
      document.getElementById(
        'Div1'
      ).style.display = 'block';
      document.getElementById(
        'Div2'
      ).style.display = 'none';
    } else {
      document.getElementById(
        'Div1'
      ).style.display = 'none';
      document.getElementById(
        'Div2'
      ).style.display = 'block';
    }
  }
}
