generatePastelColor = () => {
    let R = Math.floor((Math.random() * 127) + 127);
    let G = Math.floor((Math.random() * 127) + 127);
    let B = Math.floor((Math.random() * 127) + 127);

    let rgb = (R << 16) + (G << 8) + B;
    return `#${rgb.toString(16)}`;
}