export const findAllPartnerShipPrograms = (title) => {
    const param = "/" + (title != null ? title : '');
    return fetch('/travel-payouts/v1/partnership-programs' + param)
        .then(response => response.json());
}