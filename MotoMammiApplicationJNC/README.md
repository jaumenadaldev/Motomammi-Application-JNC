1. Per poder integrar les dades a la taula Interface, primer s'ha de executar: /appInsurance/v1/readInfoFileJNC/{resource}/{codprov}/{date} on:
    - {resource} és el recurs que es vol llegir (p.e. 'CUSTOMER', 'VEHICLES', 'PARTS')
    - {codprov} és el codi del proveidor (p.e. 'CAX', 'BBV', 'ING)
    - {date} és la data de la informació (p.e. '20240528', '20240529', '20240530')

2. Per poder integrar les dades desde la taula Interface dins la respectiva taula s'ha d'executar: /appInsurance/v1/processInfoFileJNC/{resource}/{codprov}/{date} on:
    - {resource} és el recurs que es vol integrar (p.e. 'CUSTOMER', 'VEHICLES', 'PARTS')
    - {codprov} és el codi del proveidor (p.e. 'CAX', 'BBV', 'ING)
    - {date} és la data de la informació (p.e. '20240528', '20240529', '20240530')

3. Per poder generar les factures corresponents s'ha d'executar: /appInsurance/v1/genInvoiceFileJNC/{codprov}/{date} on:
    - {codprov} és el codi del proveidor (p.e. 'CAX', 'BBV', 'ING)
    - {date} és la data de la informació (p.e. '202405', '202404', '202403')