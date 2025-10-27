#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 InSize;
uniform float Time;
uniform vec4 ColorModulate;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec2 uv = texCoord;

    // Effetto oscillante per simulare ubriachezza
    float wave = sin(Time * 0.8 + uv.y * 10.0) * 0.005 + cos(Time * 1.1 + uv.x * 8.0) * 0.005;

    // Offsets per visione doppia
    vec2 offset1 = vec2(wave, -wave);
    vec2 offset2 = vec2(-wave * 1.2, wave * 1.2);

    // Due campioni per doppia visione
    vec4 col1 = texture(DiffuseSampler, uv + offset1);
    vec4 col2 = texture(DiffuseSampler, uv + offset2);

    // Colore finale combinato
    vec4 color = vec4(col1.r, col2.g, (col1.b + col2.b) * 0.5, 1.0);

    // Vignettatura morbida
    float dist = distance(uv, vec2(0.5));
    float vignette = smoothstep(0.8, 0.3, dist);
    color.rgb *= vignette;

    // Leggera tinta rossa
    color.rgb *= vec3(1.05, 0.85, 0.85);

    fragColor = color * ColorModulate;
}
