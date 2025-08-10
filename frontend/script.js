const API = 'http://localhost:8080';

const qs = s => document.querySelector(s);
const tblBody = qs('#tbl tbody');
const fMarca = qs('#f-marca'), fAno = qs('#f-ano'), fCor = qs('#f-cor');

async function http(path, init) {
  const res = await fetch(API + path, {
    headers: { 'Content-Type': 'application/json' }, ...init
  });
  if (!res.ok) throw new Error(`${res.status} ${res.statusText}: ${await res.text()}`);
  return res.status === 204 ? null : res.json();
}

async function loadList() {
  const p = new URLSearchParams();
  if (fMarca.value.trim()) p.set('marca', fMarca.value.trim());
  if (fAno.value) p.set('ano', fAno.value);
  if (fCor.value.trim()) p.set('cor', fCor.value.trim());
  const items = await http('/veiculos' + (p.toString() ? '?' + p.toString() : ''));
  renderTable(items);
}

function renderTable(items) {
  tblBody.innerHTML = '';
  if (!items.length) {
    tblBody.innerHTML = `<tr><td colspan="7" class="muted">Sem resultados</td></tr>`;
    return;
  }
  for (const v of items) {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${v.id}</td>
      <td>${v.veiculo}</td>
      <td>${v.marca}</td>
      <td>${v.ano}</td>
      <td>${v.cor}</td>
      <td>${v.vendido ? 'Sim' : 'Não'}</td>
      <td class="row" style="gap:6px">
        <button data-edit="${v.id}">Editar</button>
        <button data-del="${v.id}" class="danger">Excluir</button>
      </td>`;
    tblBody.appendChild(tr);
  }
}

// Ações da tabela
tblBody.addEventListener('click', async (e) => {
  const btn = e.target.closest('button'); if (!btn) return;
  const id = btn.dataset.edit || btn.dataset.del;
  if (btn.dataset.edit) fillForm(await http(`/veiculos/${id}`));
  if (btn.dataset.del) {
    if (!confirm('Apagar veículo ' + id + '?')) return;
    await http(`/veiculos/${id}`, { method: 'DELETE' });
    await loadAll();
  }
});

// Filtros
qs('#btn-filtrar').onclick = loadList;
qs('#btn-limpar').onclick = () => { fMarca.value=''; fAno.value=''; fCor.value=''; loadList(); };

// Form
const form = qs('#form');
const hidId = qs('#hid-id');
const formTitle = qs('#form-title');
const msg = qs('#msg');

function fillForm(v) {
  hidId.value = v.id;
  qs('#veiculo').value = v.veiculo;
  qs('#marca').value = v.marca;
  qs('#ano').value = v.ano;
  qs('#cor').value = v.cor;
  qs('#descricao').value = v.descricao || '';
  qs('#vendido').checked = !!v.vendido;
  formTitle.textContent = `Editar veículo #${v.id}`;
}
function resetForm() {
  hidId.value = '';
  form.reset();
  formTitle.textContent = 'Novo veículo';
  msg.textContent = '';
}
qs('#btn-cancelar').onclick = resetForm;

form.onsubmit = async (ev) => {
  ev.preventDefault();
  const body = {
    veiculo: qs('#veiculo').value.trim(),
    marca: qs('#marca').value.trim(),
    ano: Number(qs('#ano').value),
    cor: qs('#cor').value.trim(),
    descricao: qs('#descricao').value.trim(),
    vendido: qs('#vendido').checked
  };
  try {
    if (hidId.value) await http(`/veiculos/${hidId.value}`, { method: 'PUT', body: JSON.stringify(body) });
    else await http('/veiculos', { method: 'POST', body: JSON.stringify(body) });
    msg.textContent = 'Salvo com sucesso.'; msg.className = 'ok';
    resetForm(); await loadAll();
  } catch (e) {
    msg.textContent = e.message; msg.className = 'danger';
  }
};

// Relatórios
async function loadReports() {
  qs('#r-nao-vendidos').textContent = await http('/veiculos/nao-vendidos');

  const porMarca = await http('/veiculos/por-marca');
  qs('#r-por-marca').innerHTML = Object.keys(porMarca).length
    ? Object.entries(porMarca).map(([m,q]) => `<li>${m}: ${q}</li>`).join('')
    : '<li class="muted">—</li>';

  const porDec = await http('/veiculos/por-decada');
  qs('#r-por-decada').innerHTML = Object.keys(porDec).length
    ? Object.entries(porDec).map(([d,q]) => `<li>${d}s: ${q}</li>`).join('')
    : '<li class="muted">—</li>';

  const ult7 = await http('/veiculos/ultimos-7-dias');
  qs('#r-ultimos7').innerHTML = ult7.length
    ? ult7.map(v => `<li>${v.id} — ${v.marca} ${v.veiculo} (${v.ano})</li>`).join('')
    : '<li class="muted">—</li>';
}

async function loadAll() { await loadList(); await loadReports(); }
loadAll();
